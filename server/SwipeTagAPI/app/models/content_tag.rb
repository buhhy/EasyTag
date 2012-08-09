class ContentTag < ActiveRecord::Base
  belongs_to :tag
  belongs_to :content

  scope :chronological, lambda { |tag| where("content_id = ?", content_id) }
  scope :for_content, lambda { |content_id| where("content_id = ?", content_id) }
  scope :for_tag,   lambda { |tag_id| where("tag_id = ?", tag_id) }
  
end
